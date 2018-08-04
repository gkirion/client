package com.george.client.domain;

public class Node {
	
	private Long id;
	private String ip;
	private Short port;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Short getPort() {
		return port;
	}
	
	public void setPort(Short port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", ip=" + ip + ", port=" + port + "]";
	}

}
