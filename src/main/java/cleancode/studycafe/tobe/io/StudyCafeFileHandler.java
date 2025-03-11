package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class StudyCafeFileHandler implements FileHandler {

    @Override
    public List<StudyCafePass> readStudyCafePasses() {

        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/pass-list.csv"));
            return parseStudyCafePasses(lines);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    @Override
    public List<StudyCafeLockerPass> readLockerPasses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/locker.csv"));
            return parseStudyCafeLockerPasses(lines);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }


    private static List<StudyCafePass> parseStudyCafePasses(List<String> lines) {

        return lines.stream()
                .map(line -> {
                    String[] values = line.split(",");
                    StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
                    int duration = Integer.parseInt(values[1]);
                    int price = Integer.parseInt(values[2]);
                    double discountRate = Double.parseDouble(values[3]);

                    return StudyCafePass.of(studyCafePassType, duration, price, discountRate);
                })
                .collect(Collectors.toList());
    }

    private static List<StudyCafeLockerPass> parseStudyCafeLockerPasses(List<String> lines) {

        return lines.stream()
                .map(line -> {
                    String[] values = line.split(",");
                    StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
                    int duration = Integer.parseInt(values[1]);
                    int price = Integer.parseInt(values[2]);

                    return StudyCafeLockerPass.of(studyCafePassType, duration, price);
                })
                .collect(Collectors.toList());
    }
}
