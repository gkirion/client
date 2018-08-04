package com.george.client.services;

public interface ClientService {

	public String get(String key) throws Exception;

	public void put(String key, String value) throws Exception;

}
