
package com.fitbitsample;

public class WeatherData {

	public WindData windData = new WindData();

	
	public  class WindData {
		private float speed;
		private float deg;
		public float getSpeed() {
			return speed;
		}
		public void setSpeed(float speed) {
			this.speed = speed;
		}
		public float getDeg() {
			return deg;
		}
		public void setDeg(float deg) {
			this.deg = deg;
		}
		
		
	}

}
