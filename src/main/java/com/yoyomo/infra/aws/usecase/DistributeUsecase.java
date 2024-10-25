package com.yoyomo.infra.aws.usecase;

import java.io.IOException;

public interface DistributeUsecase {
    void create(String subDomain) throws IOException;

    void delete(String subDomain) throws IOException;
}
