package org.home.spring.rest.common;

import javax.annotation.Nonnull;
import java.util.Objects;

public class User {
    public final String name;
    public final String surname;

    public User(@Nonnull String name, @Nonnull String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        User user = (User) other;
        return Objects.equals(name, user.name) && Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname);
    }
}
