package dao;

import model.TrainingSession;

import java.util.*;

public class FileTrainingSessionDAO implements TrainingSessionDAO {
    private final Map<String, TrainingSession> trainingSessionDB = new HashMap<>();

    @Override
    public void addTrainingSession(TrainingSession session) {
        trainingSessionDB.put(session.getId(), session);
    }

    @Override
    public TrainingSession getTrainingSessionById(String id) {
        return trainingSessionDB.get(id);
    }

    @Override
    public List<TrainingSession> getAllTrainingSessions() {
        return new ArrayList<>(trainingSessionDB.values());
    }

    @Override
    public void updateTrainingSession(TrainingSession session) {
        if (trainingSessionDB.containsKey(session.getId())) {
            trainingSessionDB.put(session.getId(), session);
        } else {
            throw new NoSuchElementException("Training session not found.");
        }
    }

    @Override
    public void deleteTrainingSession(String id) {
        if (!trainingSessionDB.containsKey(id)) {
            throw new NoSuchElementException("Training session not found.");
        }
        trainingSessionDB.remove(id);
    }
}
