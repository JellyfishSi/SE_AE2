package dao;

import java.util.List;
import model.TrainingSession;

public interface TrainingSessionDAO {
    void addTrainingSession(TrainingSession session);
    TrainingSession getTrainingSessionById(String id);
    List<TrainingSession> getAllTrainingSessions();
    void updateTrainingSession(TrainingSession session);
    void deleteTrainingSession(String id);
}


