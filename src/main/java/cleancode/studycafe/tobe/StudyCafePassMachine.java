package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.config.StudyCafeConfig;
import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.FileHandler;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {
    private final StudyCafeConfig studyCafeConfig;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final FileHandler studyCafeFileHandler;

    public StudyCafePassMachine(StudyCafeConfig studCafeConfig) {
        this.studyCafeConfig = studCafeConfig;
        this.inputHandler = studCafeConfig.getInputHandler();
        this.outputHandler = studCafeConfig.getOutputHandler();
        this.studyCafeFileHandler = studCafeConfig.getFileHandler();
    }

    public void run() {

        try {

            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();
            StudyCafePass selectedPass = selectPass();
            Optional<StudyCafeLockerPass> lockerPass = getLockerPass(selectedPass);

            lockerPass.ifPresentOrElse(
                locker -> outputHandler.showPassOrderSummary(selectedPass, locker),
                () -> outputHandler.showPassOrderSummary(selectedPass)
            );


        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectPass() {
        outputHandler.askPassTypeSelection();

        StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        List<StudyCafePass> passCandidate = getStudyCafePasses(studyCafePasses, studyCafePassType);
        outputHandler.showPassListForSelection(passCandidate);
        return inputHandler.getSelectPass(passCandidate);
    }

    private Optional<StudyCafeLockerPass> getLockerPass(StudyCafePass selectedPass) {

        if (selectedPass.getPassType() != StudyCafePassType.FIXED) {
            return Optional.empty();
        }

        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();

        StudyCafeLockerPass lockerPassCandidate = lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                                && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);


        if (lockerPassCandidate != null) {
            outputHandler.askLockerPass(lockerPassCandidate);
            boolean isLockerSelected = inputHandler.getLockerSelection();

            if (isLockerSelected) {
                return Optional.of(lockerPassCandidate);
            }
        }
        return Optional.empty();
    }

    private static List<StudyCafePass> getStudyCafePasses(List<StudyCafePass> studyCafePasses,
                                                          StudyCafePassType passType) {
        return studyCafePasses.stream()
            .filter(studyCafePass -> studyCafePass.getPassType() == passType)
            .toList();
    }

}
