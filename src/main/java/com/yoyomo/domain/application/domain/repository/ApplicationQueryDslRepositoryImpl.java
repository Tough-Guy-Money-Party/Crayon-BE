package com.yoyomo.domain.application.domain.repository;

import static com.yoyomo.domain.application.domain.entity.QApplication.*;
import static com.yoyomo.domain.application.domain.entity.QEvaluation.*;
import static com.yoyomo.domain.application.domain.entity.QProcessResult.*;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoyomo.domain.application.application.usecase.dto.ApplicationCondition;
import com.yoyomo.domain.application.domain.repository.dto.ApplicationWithStatus;
import com.yoyomo.domain.application.domain.vo.condition.EvaluationFilter;
import com.yoyomo.domain.application.domain.vo.condition.ResultFilter;
import com.yoyomo.domain.application.domain.vo.condition.SortType;
import com.yoyomo.domain.recruitment.domain.entity.Process;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class ApplicationQueryDslRepositoryImpl implements ApplicationQueryDslRepository {

	private static final Map<SortType, OrderSpecifier<?>> ORDER_SPECIFIER_MAP = Map.of(
		SortType.NAME, new OrderSpecifier<>(Order.ASC, application.userName),
		SortType.APPLIED, new OrderSpecifier<>(Order.DESC, application.createdAt)
	);

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<ApplicationWithStatus> findAllWithStatusByProcess(
		Process process, ApplicationCondition condition, Pageable pageable
	) {
		List<ApplicationWithStatus> result = queryFactory.select(Projections.constructor(
				ApplicationWithStatus.class, application, processResult.status
			))
			.from(application)
			.leftJoin(processResult)
			.on(
				application.id.eq(processResult.applicationId)
					.and(application.process.id.eq(processResult.processId))
			)
			.leftJoin(evaluation)
			.on(
				application.id.eq(evaluation.application.id)
			)
			.where(
				application.process.eq(process),
				application.deletedAt.isNull(),
				eqResult(condition.resultFilter()),
				notnullEvaluation(condition.evaluationFilter())
			)
			.orderBy(ORDER_SPECIFIER_MAP.get(condition.sortType()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long totalCount = queryFactory
			.select(application.countDistinct())
			.from(application)
			.leftJoin(processResult)
			.on(
				application.id.eq(processResult.applicationId)
					.and(application.process.id.eq(processResult.processId))
			)
			.leftJoin(evaluation)
			.on(
				application.id.eq(evaluation.application.id)
			)
			.where(
				application.process.eq(process),
				application.deletedAt.isNull(),
				eqResult(condition.resultFilter()),
				notnullEvaluation(condition.evaluationFilter())
			).fetchOne();
		return PageableExecutionUtils.getPage(result, pageable, () -> totalCount != null ? totalCount : 0L);
	}

	private BooleanExpression notnullEvaluation(EvaluationFilter filter) {
		if (StringUtils.isNullOrEmpty(filter.name()) || filter.isAll()) {
			return null;
		}
		return filter == EvaluationFilter.YES ? evaluation.isNotNull() : evaluation.isNull();
	}

	private BooleanExpression eqResult(ResultFilter filter) {
		if (StringUtils.isNullOrEmpty(filter.name()) || filter.isAll()) {
			return null;
		}
		if (filter.isWithoutResult()) {
			return processResult.isNull();
		}
		return processResult.status.stringValue().containsIgnoreCase(filter.name());
	}
}
