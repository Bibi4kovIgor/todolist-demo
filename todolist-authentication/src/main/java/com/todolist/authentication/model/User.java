package com.todolist.authentication.model;

import java.io.Serializable;

public record User(String username, String password) implements Serializable {

}
