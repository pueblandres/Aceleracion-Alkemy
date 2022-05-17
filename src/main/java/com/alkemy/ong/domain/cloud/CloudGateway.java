package com.alkemy.ong.domain.cloud;

import java.io.IOException;

public interface CloudGateway {

    CloudOutput save(CloudInput cloud) throws IOException;

}
