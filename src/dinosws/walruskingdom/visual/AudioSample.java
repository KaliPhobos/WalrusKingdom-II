package dinosws.walruskingdom.visual;

public class AudioSample {

	public AudioSample() {
		// TODO Auto-generated constructor stub
		
		/** Whether to wait for all previous samples to finish, before playing this one. */
		boolean awaitStart;
		
		/** Whether to wait for the current sample to finish, before playing another one. */
		boolean awaitEnd;
		long creationTime;
		long expirationTime;
		int volume;
	}

}
