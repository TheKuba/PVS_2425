package streaming;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BloodDonationStreams {
    public static void main(String[] args) {
        List<BloodDonation> donations = BloodDonation.generateSampleDonations(100);
        donations.forEach(System.out::println);

      double avg =  donations.stream()
                .filter(b -> b.getDonationType().equals("Plasma"))
                .mapToDouble(BloodDonation::getAmountMl)
                .average()
                .orElse(0);

      long count = donations.stream()
              .filter(b -> b.getDonorId().equals("D003"))
              .count();

      //3 nejvetsi darovani
        donations.stream()
                .sorted((b1, b2) -> b1.getAmountMl() - b2.getAmountMl())
                .limit(3)
                .forEach(System.out::println);

        //mapa vsech darovani dle typu
        donations.stream()
                .collect(Collectors.groupingBy(BloodDonation::getDonationType));
    }
}

class BloodDonation {
    private String donationId;
    private String donorId;
    private String bloodType;
    private int amountMl;
    private LocalDate donationDate;
    private String center;
    private String status;
    private String donationType;

    public BloodDonation(String donationId, String donorId, String bloodType, int amountMl,
                         LocalDate donationDate, String center, String status, String donationType) {
        this.donationId = donationId;
        this.donorId = donorId;
        this.bloodType = bloodType;
        this.amountMl = amountMl;
        this.donationDate = donationDate;
        this.center = center;
        this.status = status;
        this.donationType = donationType;
    }

    public String getDonationId() { return donationId; }
    public String getDonorId() { return donorId; }
    public String getBloodType() { return bloodType; }
    public int getAmountMl() { return amountMl; }
    public LocalDate getDonationDate() { return donationDate; }
    public String getCenter() { return center; }
    public String getStatus() { return status; }
    public String getDonationType() { return donationType; }

    @Override
    public String toString() {
        return String.format(
                "BloodDonation{ID=%s, Donor=%s, BloodType=%s, Amount=%dml, Date=%s, Center=%s, Status=%s, Type=%s}",
                donationId, donorId, bloodType, amountMl, donationDate, center, status, donationType
        );
    }

    public static List<BloodDonation> generateSampleDonations(int count) {
        Random random = new Random();

        List<String> donors = Arrays.asList("D001", "D002", "D003", "D004", "D005", "D006");
        List<String> bloodTypes = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "0+", "0-");
        List<String> centers = Arrays.asList("Prague Central", "Brno Hospital", "Ostrava Clinic", "Plzen Center");
        List<String> statuses = Arrays.asList("Completed", "Rejected", "Pending");
        List<String> donationTypes = Arrays.asList("Whole Blood", "Plasma", "Platelets");

        return IntStream.range(0, count)
                .mapToObj(i -> new BloodDonation(
                        "BD" + (1000 + i),
                        donors.get(random.nextInt(donors.size())),
                        bloodTypes.get(random.nextInt(bloodTypes.size())),
                        450 + random.nextInt(151), // 450-600 ml
                        LocalDate.now().minusDays(random.nextInt(180)),
                        centers.get(random.nextInt(centers.size())),
                        statuses.get(random.nextInt(statuses.size())),
                        donationTypes.get(random.nextInt(donationTypes.size()))
                ))
                .collect(Collectors.toList());
    }
}