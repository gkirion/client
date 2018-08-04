package com.george.client.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.george.client.services.ClientService;

@RestController
public class ClientController {

	@Autowired
	ClientService clientService;

	@RequestMapping(path = "/client", method = RequestMethod.POST)
	public void put(@RequestParam String key, @RequestParam String value) {
		try {
			clientService.put(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(path = "/client", method = RequestMethod.GET)
	public String get(@RequestParam String key) {
		try {
			return clientService.get(key);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}
