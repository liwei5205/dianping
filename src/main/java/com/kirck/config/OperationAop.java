package com.kirck.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OperationAop {
	 private static Logger log = LoggerFactory.getLogger(OperationAop.class);
	 
	 	@Before("execution(* com.kirck.mapper.*.select*(..)) || execution(* com.kirck.mapper.*.count*(..))")
		public void OperationRead() {
			log.info("OperationRead切换到：Read");
		}
	 
		@Before("execution(* com.kirck.mapper.*.insert*(..)) || execution(* com.kirck.mapper.*.update*(..)) || execution(* com.kirck.mapper.*.delete*(..))")
		public void OperationWrite() {
			log.info("OperationRead切换到：write");
		}

}
