package com.todolistservice.model;

import java.io.Serializable;

public record Task(int id, String text) implements Serializable {   }

