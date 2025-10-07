import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HashTest {
    public static void main(String[] args) {
        // Test genesis block (index 0) - 2025-09-30 20:50:51.196156
        LocalDateTime genesisTime = LocalDateTime.of(2025, 9, 30, 20, 50, 51, 196156000);
        String genesisDonationStr = "Donation{id=2, donorName='Genesis', amount=0.00, timestamp=2025-09-30T20:50:51.196125, message='Genesis Block'}";
        String genesisHash = calculateHashOld(0, genesisDonationStr, "0", genesisTime.toString());
        System.out.println("Genesis calculated hash: " + genesisHash);
        System.out.println("Genesis stored hash:     a053e662a7b40f4810b5933829394718efaf4777721fdd256e9a65cfd1b9be8a");

        // Test block 1 (index 1) - 2025-09-30 16:25:42.272293
        LocalDateTime block1Time = LocalDateTime.of(2025, 9, 30, 16, 25, 42, 272293000);
        String block1DonationStr = "Donation{id=3, donorName='a', amount=1000.00, timestamp=2025-09-30T16:25:42.242261, message=''}";
        String block1Hash = calculateHashOld(1, block1DonationStr, "a053e662a7b40f4810b5933829394718efaf4777721fdd256e9a65cfd1b9be8a", block1Time.toString());
        System.out.println("Block 1 calculated hash: " + block1Hash);
        System.out.println("Block 1 stored hash:     3971f3fba0acb1da953d2ec6711f03b33ae15d8c4777ed1ec1b0f3a90498766f");

        // Test block 2 (index 2) - 2025-09-30 16:25:59.066759
        LocalDateTime block2Time = LocalDateTime.of(2025, 9, 30, 16, 25, 59, 66759000);
        String block2DonationStr = "Donation{id=4, donorName='a', amount=1000.00, timestamp=2025-09-30T16:25:59.063985, message='hi'}";
        String block2Hash = calculateHashOld(2, block2DonationStr, "3971f3fba0acb1da953d2ec6711f03b33ae15d8c4777ed1ec1b0f3a90498766f", block2Time.toString());
        System.out.println("Block 2 calculated hash: " + block2Hash);
        System.out.println("Block 2 stored hash:     5ec1fb6106ce8e191bb82527ce21e647438bce5df570fb894f5cd8e59d0e2c9b");

        // Test block 3 (index 3) - 2025-09-30 16:36:34.415509
        LocalDateTime block3Time = LocalDateTime.of(2025, 9, 30, 16, 36, 34, 415509000);
        String block3DonationStr = "Donation{id=5, donorName='a', amount=1000.00, timestamp=2025-09-30T16:36:34.369122, message='hi'}";
        String block3Hash = calculateHashOld(3, block3DonationStr, "5ec1fb6106ce8e191bb82527ce21e647438bce5df570fb894f5cd8e59d0e2c9b", block3Time.toString());
        System.out.println("Block 3 calculated hash: " + block3Hash);
        System.out.println("Block 3 stored hash:     827821ba2d7621915f906ebee98b0568df36c89d4fc0fa7e5f23cc0bfb1a7bd9");
    }

    public static String calculateHashOld(int index, String donationStr, String previousHash, String timestampStr) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dataToHash = index + donationStr + previousHash + timestampStr;
            byte[] hashBytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
