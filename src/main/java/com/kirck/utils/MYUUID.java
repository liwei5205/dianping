package com.kirck.utils;

import java.security.SecureRandom;

public final class MYUUID implements java.io.Serializable, Comparable<MYUUID>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5977047798807067604L;

	/*
	 * The most significant 64 bits of this UUID.
	 *
	 * @serial
	 */
	private final long mostSigBits;

	/*
	 * The least significant 64 bits of this UUID.
	 *
	 * @serial
	 */
	private final long leastSigBits;

	@Override
	public int compareTo(MYUUID val) {
		return (this.mostSigBits < val.mostSigBits ? -1 :
				(this.mostSigBits > val.mostSigBits ? 1 :
						(this.leastSigBits < val.leastSigBits ? -1 :
								(this.leastSigBits > val.leastSigBits ? 1 :
										0))));
	}


	private static class Holder {
		static final SecureRandom numberGenerator = new SecureRandom();
	}
	private static String digits(long val, int digits) {
		long hi = 1L << (digits * 4);
		return Long.toHexString(hi | (val & (hi - 1))).substring(1);
	}

	private MYUUID(byte[] data) {
		long msb = 0;
		long lsb = 0;
		assert data.length == 16 : "data must be 16 bytes in length";
		for (int i=0; i<8; i++)
			msb = (msb << 8) | (data[i] & 0xff);
		for (int i=8; i<16; i++)
			lsb = (lsb << 8) | (data[i] & 0xff);
		this.mostSigBits = msb;
		this.leastSigBits = lsb;
	}

	public static MYUUID randomUUID() {
		SecureRandom ng = Holder.numberGenerator;

		byte[] randomBytes = new byte[16];
		ng.nextBytes(randomBytes);
		randomBytes[6]  &= 0x0f;  /* clear version        */
		randomBytes[6]  |= 0x40;  /* set to version 4     */
		randomBytes[8]  &= 0x3f;  /* clear variant        */
		randomBytes[8]  |= 0x80;  /* set to IETF variant  */
		return new MYUUID(randomBytes);
	}

	public String toString() {
		return (digits(mostSigBits >> 32, 8) +
				digits(mostSigBits >> 16, 4) +
				digits(mostSigBits, 4) +
				digits(leastSigBits >> 48, 4) +
				digits(leastSigBits, 12));
	}

}
