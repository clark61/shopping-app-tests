/*
 * Project
 * Topic: Cucumber
 * Author: Nick Clark
 */

package edu.depaul.se433.shoppingapp;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Starts the Cucumber feature file.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/shoppingapp.feature")
class CucumberTestRunner {
}
