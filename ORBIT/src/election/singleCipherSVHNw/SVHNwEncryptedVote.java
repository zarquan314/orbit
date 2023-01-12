package election.singleCipherSVHNw;

import java.math.BigInteger;
import java.security.SecureRandom;

import blah.AdditiveCiphertext;
import blah.Additive_Pub_Key;
import election.EncryptedVote;
import zero_knowledge_proofs.CryptoData.CryptoData;

public class SVHNwEncryptedVote implements EncryptedVote {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2151827978056330592L;
	private AdditiveCiphertext cipher;
	private CryptoData[] transcript;
	
	public SVHNwEncryptedVote(AdditiveCiphertext cipher, CryptoData[] transcript) {
		this.cipher = cipher;
		this.transcript = transcript;
	}

	@Override
	public CryptoData[] getProofTranscript() {
		return transcript.clone();
	}

	@Override
	public EncryptedVote rerandomize(SecureRandom rand, Additive_Pub_Key raceKey) {
		BigInteger r = raceKey.generateEphemeral(rand);
		return rerandomize(new BigInteger[] {r}, raceKey);
	}

	@Override
	public EncryptedVote rerandomize(BigInteger[] r, Additive_Pub_Key raceKey) {
		
		//Rerandomize
		AdditiveCiphertext cipher2 = (AdditiveCiphertext) cipher.rerandomize(r[0], raceKey);
		
		return new SVHNwEncryptedVote(cipher2, null);
	}

	@Override
	public Object getCiphertext() {
		return cipher;
	}

}
