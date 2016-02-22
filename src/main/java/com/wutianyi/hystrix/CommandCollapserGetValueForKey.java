package com.wutianyi.hystrix;

import com.google.api.client.util.Lists;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import org.hibernate.engine.jdbc.batch.spi.Batch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hanjiewu on 2016/2/3.
 */
public class CommandCollapserGetValueForKey extends HystrixCollapser<List<String>, String, Integer> {

	private final Integer key;

	public CommandCollapserGetValueForKey(Integer key) {
		this.key = key;
	}

	@Override
	public Integer getRequestArgument() {
		return key;
	}

	@Override
	protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
		return new BatchCommand(collapsedRequests);
	}

	@Override
	protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
		int count = 0;
		for (CollapsedRequest<String, Integer> request : collapsedRequests) {
			request.setResponse(batchResponse.get(count++));
		}
	}

	private static final class BatchCommand extends HystrixCommand<List<String>> {

		private final Collection<CollapsedRequest<String, Integer>> requests;

		public BatchCommand(Collection<CollapsedRequest<String, Integer>> requests) {
			super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")).andCommandKey
					(HystrixCommandKey.Factory.asKey("GetValueForKey")));
			this.requests = requests;
		}

		@Override
		protected List<String> run() throws Exception {
			ArrayList<String> response = Lists.newArrayList();
			for (CollapsedRequest<String, Integer> request : requests) {
				response.add("ValueForKey: " + request.getArgument());
			}
			return response;
		}
	}
}
