package ru.javawebinar.topjava.to;

import java.time.LocalDateTime;
import java.util.Objects;

public class MealTo {
    private Integer id;

    private LocalDateTime dateTime;

    private String description;

    private int calories;

    private boolean excess;

    public MealTo(Integer id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public MealTo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        // Reflexive
        if (this == obj) {
            return true;
        }
        // Null-handling
        if (obj == null) {
            return false;
        }
        // Different types can't be equal
        if (getClass() != obj.getClass()) {
            return false;
        }
        // Let the helper do the rest
        MealTo other = (MealTo) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.calories, other.calories)
                && Objects.equals(this.dateTime, other.dateTime)
                && Objects.equals(this.description, other.description)
                && Objects.equals(this.excess, other.excess)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.calories, this.dateTime, this.description, this.excess);
    }
}
