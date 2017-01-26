package de.webdataplatform.system;

import java.util.concurrent.atomic.AtomicLong;

public class StatisticsElement {

	private AtomicLong countUpdateMeasurements;
	private AtomicLong totalUpdates;
	private AtomicLong throughput;
	private AtomicLong avgThroughput;
	private AtomicLong maxThroughput;
	
	private AtomicLong countLatencyMeasurements;
	private AtomicLong totalLatency;
	private AtomicLong latency;
	private AtomicLong avgLatency;
	private AtomicLong maxLatency;
	
	
	public StatisticsElement(){
		
		countUpdateMeasurements = new AtomicLong();
		totalUpdates = new AtomicLong();
		throughput = new AtomicLong();
		avgThroughput = new AtomicLong();
		maxThroughput = new AtomicLong();
		
		countLatencyMeasurements = new AtomicLong();
		totalLatency = new AtomicLong();		
		latency = new AtomicLong();
		avgLatency = new AtomicLong();
		maxLatency = new AtomicLong();
		
		
		
	}
	
	
	public synchronized void recordUpdate(){
		
		totalUpdates.incrementAndGet();
		throughput.incrementAndGet();
	}
	
	
	public void measureTroughput(){
		
		
		if(maxThroughput.intValue() < throughput.intValue()){
			maxThroughput.set(throughput.intValue());
		}
		
		if(throughput.intValue() != 0){
			countUpdateMeasurements.incrementAndGet();
			avgThroughput.set(totalUpdates.intValue()/countUpdateMeasurements.intValue());
		}
		throughput.set(0);
	
	}
	
	public void recordLatency(long nanoseconds){
		
		totalLatency.addAndGet(nanoseconds/1000);
		countLatencyMeasurements.incrementAndGet();
		latency.set(nanoseconds/1000);
	}
	
	public void measureLatency(){
		
		if(maxLatency.intValue() < latency.intValue()){
			maxLatency.set(latency.intValue());
		}
		
		if(countLatencyMeasurements.intValue()!= 0)avgLatency.set((totalLatency.intValue()/countLatencyMeasurements.intValue()));
		
	
	}


	public AtomicLong getCountUpdateMeasurements() {
		return countUpdateMeasurements;
	}


	public void setCountUpdateMeasurements(AtomicLong countUpdateMeasurements) {
		this.countUpdateMeasurements = countUpdateMeasurements;
	}


	public AtomicLong getTotalUpdates() {
		return totalUpdates;
	}


	public void setTotalUpdates(AtomicLong totalUpdates) {
		this.totalUpdates = totalUpdates;
	}


	public AtomicLong getThroughput() {
		return throughput;
	}


	public void setThroughput(AtomicLong throughput) {
		this.throughput = throughput;
	}


	public AtomicLong getAvgThroughput() {
		return avgThroughput;
	}


	public void setAvgThroughput(AtomicLong avgThroughput) {
		this.avgThroughput = avgThroughput;
	}


	public AtomicLong getMaxThroughput() {
		return maxThroughput;
	}


	public void setMaxThroughput(AtomicLong maxThroughput) {
		this.maxThroughput = maxThroughput;
	}


	public AtomicLong getCountLatencyMeasurements() {
		return countLatencyMeasurements;
	}


	public void setCountLatencyMeasurements(AtomicLong countLatencyMeasurements) {
		this.countLatencyMeasurements = countLatencyMeasurements;
	}


	public AtomicLong getTotalLatency() {
		return totalLatency;
	}


	public void setTotalLatency(AtomicLong totalLatency) {
		this.totalLatency = totalLatency;
	}


	public AtomicLong getLatency() {
		return latency;
	}


	public void setLatency(AtomicLong latency) {
		this.latency = latency;
	}


	public AtomicLong getAvgLatency() {
		return avgLatency;
	}


	public void setAvgLatency(AtomicLong avgLatency) {
		this.avgLatency = avgLatency;
	}


	public AtomicLong getMaxLatency() {
		return maxLatency;
	}


	public void setMaxLatency(AtomicLong maxLatency) {
		this.maxLatency = maxLatency;
	}



	
	
	
	
	
	
	
	
	
	
	
	

}
