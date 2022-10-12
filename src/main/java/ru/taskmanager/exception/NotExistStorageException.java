package ru.taskmanager.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Резюме " + uuid + " не найдено", uuid);
    }
}
