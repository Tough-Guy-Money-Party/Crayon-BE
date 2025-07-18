package com.yoyomo.domain.application.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

	SUCCESS_SAVE("모집 지원에 성공했습니다."),
	SUCCESS_READ_ALL("지원서 목록 조회에 성공했습니다."),
	SUCCESS_READ("지원서 조회에 성공했습니다."),
	SUCCESS_SEARCH("지원서 검색에 성공했습니다."),
	SUCCESS_UPDATE("지원서 수정에 성공했습니다."),
	SUCCESS_SAVE_INTERVIEW("면접 설정에 성공했습니다."),
	SUCCESS_SAVE_EVALUATION("평가 생성에 성공했습니다."),
	SUCCESS_READ_EVALUATION("평가 조회에 성공했습니다."),
	SUCCESS_UPDATE_EVALUATION("평가 수정에 성공했습니다."),
	SUCCESS_DELETE_EVALUATION("평가 삭제에 성공했습니다."),
	SUCCESS_READ_RESULT("모집 결과 조회에 성공했습니다."),
	SUCCESS_MOVE_PASS("합격자 이동에 성공했습니다."),

	SUCCESS_SAVE_INTERVIEW_RECORD("면접 기록 생성에 성공했습니다."),
	SUCCESS_READ_INTERVIEW_RECORD("면접 기록 조회에 성공했습니다."),
	SUCCESS_DELETE_INTERVIEW_RECORD("면접 기록 삭제에 성공했습니다."),
	SUCCESS_UPDATE_INTERVIEW_RECORD("면접 기록 수정에 성공했습니다."),

	SUCCESS_GENERATE_CODE("이메일 인증 코드 발송에 성공했습니다."),
	SUCCESS_VERIFY_CODE("이메일 인증에 성공했습니다."),

	OVER_DEADLINE("모집 기간이 아닙니다."),
	APPLICATION_NOT_FOUND("지원 이력이 존재하지 않습니다."),
	ANSWER_NOT_FOUND("답변 이력이 존재하지 않습니다."),
	EVALUATION_NOT_FOUND("평가 이력이 존재하지 않습니다."),
	EVALUATION_ALREADY_EXIST("이미 평가 이력이 존재합니다."),
	INTERVIEW_RECORD_ALREADY_EXIST("이미 면접 기록 이력이 존재합니다."),
	INTERVIEW_RECORD_NOT_FOUND("면접 기록이 존재하지 않습니다."),
	CANNOT_SELF_DELETE("본인은 삭제할 수 없습니다."),
	CANNOT_MEMO_UPDATE("메모 수정에 실패했습니다."),
	CANNOT_MEMO_DELETE("메모 삭제에 실패했습니다."),
	ACCESS_DENIED("권한이 없습니다."),
	ALREADY_APPLIED("이미 지원한 사용자 입니다."),
	FAILED_BATCH_INSERT("지원서 저장에 실패했습니다."),
	INVALID_APPLICANT_INFO("지원하지 않는 지원자 정보입니다."),
	INVALID_DATE_FORMAT("지원하지 않는 날짜 형식입니다."),
	INVALID_DATA_TYPE("지원하지 않는 데이터 타입입니다."),
	QUESTION_REPLY_SIZE_MISMATCH("질문과 답변의 개수가 일치하지 않습니다."),
	APPLICATION_REPLY_SIZE_MISMATCH("지원서와 답변의 개수가 일치하지 않습니다."),
	INVALID_SORT_TYPE("지원하지 않는 정렬 방식입니다."),
	INVALID_FILTER_TYPE("지원하지 않는 필터링 방식입니다.");
	private final String message;
}
