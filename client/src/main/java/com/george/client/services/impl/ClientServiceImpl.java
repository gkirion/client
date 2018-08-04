package com.george.client.services.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.george.client.domain.Node;
import com.george.client.services.ClientService;

@Service("Client")
public class ClientServiceImpl implements ClientService {

	private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

	private final TreeMap<Long, Node> nodes = new TreeMap<Long, Node>();

	public String get(String key) throws Exception {
		try {
			Long id = computeId(key);
			Node node = findNode(id);
			RestTemplate restTemplate = new RestTemplate();
			String value = restTemplate.getForObject(
					String.format("http://%s:%s/node?key=%s", node.getIp(), node.getPort(), key), String.class);
			return value;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("Could not get key", e);
		}
	}

	public void put(String key, String value) throws Exception {
		try {
			Long id = computeId(key);
			Node node = findNode(id);
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForObject(
					String.format("http://%s:%s/node?key=%s&value=%s", node.getIp(), node.getPort(), key, value), null,
					String.class);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(String.format("Could not store key %s with value %s", key, value), e);
		}

	}

	@PostConstruct
	private void getNodes() {
		log.info("getting list of available nodes");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Node>> response = restTemplate.exchange("http://localhost:8080/thiroros", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Node>>() {
				});
		for (Node node : response.getBody()) {
			nodes.put(node.getId(), node);
		}
		log.info(response.getBody().toString());
		log.info(nodes.toString());
	}

	private Node findNode(Long id) {
		if (nodes.size() == 0) {
			return null;
		}
		Long key = nodes.ceilingKey(id);
		if (key == null) {
			key = nodes.ceilingKey(0L);
		}
		return nodes.get(key);
	}

	private Long computeId(String key) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
		byte[] digest = messageDigest.digest(key.getBytes());
		return byteArray2Integer(digest);
	}

	private Long byteArray2Integer(byte[] bytes) {
		long result = 0;
		for (int i = 0; i < 3; i++) {
			result += ((bytes[i] & 0xFF) << (i * 8));
		}
		result += ((bytes[3] & 0x7F) << (3 * 8));
		return result;
	}

}
