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
Compare the current score with the previous to flag alerts in condition.

Please view this in raw format to view the tables as they are printed to console.

Name: Patient 1
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Property          | Observation | Score | Comment                                                                     |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Air or Oxygen     | 2           | 2     | The patient requires supplementary oxygen.                                  |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Consciousness     | 0           | 0     | The patient is conscious.                                                   |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Respiration Range | 20          | 0     |                                                                             |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| SpO2              | 95          | 1     | As the patient is breathing oxygen, this is elevated.                       |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Temperature       | 37.2        | 0     |                                                                             |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| CBG               | 7.0         | 3     | The patient is fasting and their blood sugar is high.                       |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
The patient's final Medi score is 6.

Patient 1 Has no previous score to compare with.

Name: Patient 1
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Property          | Observation | Score | Comment                                                                     |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Air or Oxygen     | 0           | 0     | The patient is breathing air, and does not require supplementary oxygen.    |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Consciousness     | 3           | 3     | The patient is unconscious or confused.                                     |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Respiration Range | 30          | 3     |                                                                             |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| SpO2              | 70          | 3     | The patient's oxygen saturation is dangerously low.                         |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Temperature       | 33.2        | 3     |                                                                             |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| CBG               | 3.9         | 3     | The patient isn't fasting so their blood sugar is low.                      |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
The patient's final Medi score is 15.

ALERT!!!! Patient 1 MediScore has changed by more than 2 points in the last 24 hours.

Name: Patient 2
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Property          | Observation | Score | Comment                                                                     |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Air or Oxygen     | 0           | 0     | The patient is breathing air, and does not require supplementary oxygen.    |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Consciousness     | 1           | 3     | The patient is unconscious or confused.                                     |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Respiration Range | 17          | 0     |                                                                             |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| SpO2              | 80          | 3     | The patient's oxygen saturation is dangerously low.                         |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Temperature       | 37.2        | 0     |                                                                             |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| CBG               | 9.0         | 3     | The patient is fasting and their blood sugar is high.                       |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
The patient's final Medi score is 9.

Patient 2 Has no previous score to compare with.

Name: Patient 2
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Property          | Observation | Score | Comment                                                                     |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Air or Oxygen     | 0           | 0     | The patient is breathing air, and does not require supplementary oxygen.    |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Consciousness     | 1           | 3     | The patient is unconscious or confused.                                     |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Respiration Range | 17          | 0     |                                                                             |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| SpO2              | 80          | 3     | The patient's oxygen saturation is dangerously low.                         |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Temperature       | 37.2        | 0     |                                                                             |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| CBG               | 9.0         | 3     | The patient is fasting and their blood sugar is high.                       |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
The patient's final Medi score is 9.

Patient 2 MediScore has not changed by more than 2 points in the last 24 hours.

Name: Patient 3
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Property          | Observation | Score | Comment                                                                     |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Air or Oxygen     | 2           | 2     | The patient requires supplementary oxygen.                                  |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Consciousness     | 0           | 0     | The patient is conscious.                                                   |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Respiration Range | 23          | 2     |                                                                             |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| SpO2              | 88          | 2     | This is a normal range for patients breathing either air or oxygen.         |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Temperature       | 38.5        | 1     |                                                                             |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| CBG               | 6.0         | 3     | The patient is fasting and their blood sugar is high.                       |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
The patient's final Medi score is 10.

Patient 3 Has no previous score to compare with.

Name: Patient 3
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Property          | Observation | Score | Comment                                                                     |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Air or Oxygen     | 2           | 2     | The patient requires supplementary oxygen.                                  |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Consciousness     | 0           | 0     | The patient is conscious.                                                   |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Respiration Range | 23          | 2     |                                                                             |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| SpO2              | 88          | 2     | This is a normal range for patients breathing either air or oxygen.         |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| Temperature       | 38.5        | 1     |                                                                             |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
| CBG               | 6.0         | 3     | The patient is fasting and their blood sugar is high.                       |
+-------------------+-------------+-------+-----------------------------------------------------------------------------+
The patient's final Medi score is 10.

Patient 3 MediScore has not changed by more than 2 points in the last 24 hours.


