package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.auth.UserRole;

public interface SecuredCommand extends Command {
    UserRole requiredRole();
}