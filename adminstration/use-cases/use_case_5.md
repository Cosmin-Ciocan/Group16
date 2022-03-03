# USE CASE: 5  Produce report of a city

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *ONU statistician* I want *to produce report of a city* so that *I can have more information about a city*

### Scope

ONU

### Level

Primary task

### Preconditions

We know the city.  Database contains current city

### Success End Condition

A report is available for the ONU statistician to provide to ONU.

### Failed End Condition

No report is produced.

### Primary Actor

ONU statistician

### Trigger

A request for city information is sent to the ONU statistician.

## MAIN SUCCESS SCENARIO

1. ONU request information for a given city.
2. ONU statistician captures city to get information for.
3. ONU statistician extracts current information of the current city.
4. ONU statistician provides report to ONU.

## EXTENSIONS

3. **City does not exist**:
   1. ONU statistician informs ONU no city exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Final release 
