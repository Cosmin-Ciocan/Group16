# USE CASE: 1  Produce a population report of a continent

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *ONU statistician* I want *to produce a population report of a continent* so that *I can have more information about a population of a continent*

### Scope

ONU 

### Level

Primary task

### Preconditions

We know the continent.  Database contains current continent & is population

### Success End Condition

A report is available for the ONU statistician to provide to ONU.

### Failed End Condition

No report is produced.

### Primary Actor

ONU statistician

### Trigger

A request for continent population information is sent to the ONU statistician.

## MAIN SUCCESS SCENARIO

1. ONU request population information for a given continent.
2. ONU statistician captures continent to get population information for.
3. ONU statistician extracts current population information of the current continent.
4. ONU statistician provides report to ONU.

## EXTENSIONS

3. **Continent does not exist**:
    1. ONU statistician informs ONU no continent exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Final release 
