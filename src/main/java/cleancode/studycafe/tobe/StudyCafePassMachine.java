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
            outputHandler.askPassTypeSelection();

            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();
            List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();

            if (studyCafePassType == StudyCafePassType.HOURLY) {
                List<StudyCafePass> hourlyPasses = studyCafePasses.stream()
                    .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.HOURLY)
                    .toList();
                outputHandler.showPassListForSelection(hourlyPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);
                outputHandler.showPassOrderSummary(selectedPass, null);
                return;
            }

            if (studyCafePassType == StudyCafePassType.WEEKLY) {
                List<StudyCafePass> weeklyPasses = studyCafePasses.stream()
                    .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.WEEKLY)
                    .toList();
                outputHandler.showPassListForSelection(weeklyPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(weeklyPasses);
                outputHandler.showPassOrderSummary(selectedPass, null);
                return;
            }

            if (studyCafePassType == StudyCafePassType.FIXED) {
                List<StudyCafePass> fixedPasses = studyCafePasses.stream()
                    .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.FIXED)
                    .toList();
                outputHandler.showPassListForSelection(fixedPasses);
                StudyCafePass selectedPass = inputHandler.getSelectPass(fixedPasses);

                List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
                StudyCafeLockerPass lockerPass = lockerPasses.stream()
                    .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                            && option.getDuration() == selectedPass.getDuration()
                    )
                    .findFirst()
                    .orElse(null);

                boolean lockerSelection = false;

                if (lockerPass != null) {
                    outputHandler.askLockerPass(lockerPass);
                    lockerSelection = inputHandler.getLockerSelection();
                }

                if (lockerSelection) {
                    outputHandler.showPassOrderSummary(selectedPass, lockerPass);
                    return;
                }
                outputHandler.showPassOrderSummary(selectedPass, null);

            }
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

}
