package com.yishenxiao.commons.service;

import java.util.List;

import com.yishenxiao.commons.beans.Version;

public interface VersionService {

	List<Version> queryByLastTime();

}
