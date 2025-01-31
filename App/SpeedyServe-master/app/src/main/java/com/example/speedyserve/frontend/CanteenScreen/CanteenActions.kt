package com.example.speedyserve.frontend.CanteenScreen

import com.example.speedyserve.Backend.Authentication.Models.Canteen

sealed interface CanteenActions {
    object fetchCanteens : CanteenActions
}