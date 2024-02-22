
Patient Class - MediScore Assessment
This repository contains the Patient class, which represents a patient undergoing medical assessment and calculates their MediScore based on vital signs.

Key Features:

Stores patient attributes like respiration, oxygen saturation, and consciousness level.
Calculates a MediScore based on individual scores for each attribute.
Provides comments to explain the individual scores and overall MediScore.
Tracks changes in MediScore over time and alerts for significant increases.
Uses enums (AirOrOxygen and Consciousness) to represent observation values with assigned scores.
Functionality:

Update patient information with new vital signs.
Calculate the MediScore based on current observations.
Compare current MediScore to previous one and alert for significant changes.
Generate comments to explain individual scores and MediScore interpretation.

Structure:

Patient.java: Contains the implementation of the Patient class.
AirOrOxygen.java and Consciousness.java: Enum definitions for air/oxygen and consciousness observation values.

Usage:

Create a Patient object with initial vital signs and fasting status.
Update patient information using the updatePatient method as needed.
Call the calculateMediScore method to calculate the updated MediScore.
Access the individual scores and comments through respective methods.
