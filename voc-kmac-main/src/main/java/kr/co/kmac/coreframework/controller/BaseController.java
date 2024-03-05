package kr.co.kmac.coreframework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 
 * @ClassName  BaseController.java
 * @Description 
 * @author mjkim
 * @since 2021. 5. 28.
 *
 */
@CrossOrigin
public class BaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
}
