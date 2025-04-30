package com.example.postproject.services;

import org.springframework.stereotype.Service;

/**
 * Синхронизированный счетчик запросов к сервису
 */
@Service
public class RequestCounter {
    private int count = 0;
    private final Object lock = new Object(); // Объект для синхронизации

    /**
     * Увеличивает счетчик на 1 и возвращает новое значение
     */
    public int increment() {
        synchronized (lock) {
            count++;
            return count;
        }
    }

    /**
     * Возвращает текущее значение счетчика
     */
    public int getCount() {
        synchronized (lock) {
            return count;
        }
    }

    /**
     * Сбрасывает счетчик в 0
     */
    public void reset() {
        synchronized (lock) {
            count = 0;
        }
    }
}