package com.bootdo.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.bootdo.common.config.BootdoConfig;

public class BaseServiceImpl {

	@Autowired
	protected BootdoConfig bootdoConfig;
}
