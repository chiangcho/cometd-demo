package com.cvicse.ts.controller;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cvicse.ts.cometd.service.TimesBiddingTimedTaskService;

@Controller
@RequestMapping("/chat")
public class CometdController {
	@Autowired
	private TimesBiddingTimedTaskService timesBiddingTimedTaskService;
	@ResponseBody
	@RequestMapping(value = "/add")
	public String addChat(@RequestParam("message") String message,@RequestParam("room")String room) {
		timesBiddingTimedTaskService.chetPublic(room, message, (new Date()).toLocaleString());
		return "ok";

	}
}