# Zendesk Batch Multi Source


Description
-----------
This source reads objects from Zendesk.
Examples of objects are Article Comments, Post Comments, Requests Comments, Ticket Comments,
Groups, Organizations, Satisfaction Ratings, Tags, Ticket Fields,
Ticket Metrics, Ticket Metric Events, Tickets, Users.
Tags object lists the 500 most popular tags in the last 60 days.

The data which should be read is specified using object and filters for that object.

Configuration
-------------

### Basic

**Reference Name:** Name used to uniquely identify this source for lineage, annotating metadata, etc.

**Admin Email:** Zendesk admin email.

**API Token:** Zendesk API token. Can be obtained from the Zendesk Support Admin interface.
Check out [Zendesk documentation](https://support.zendesk.com/hc/en-us/articles/226022787-Generating-a-new-API-token-)
for API Token generation.

**Subdomains:** List of Zendesk Subdomains to read object from.

**Objects to Pull:** Objects to pull from Zendesk API. Supports multi-select.

**Objects to Skip:** Objects to skip from Zendesk API. Supports multi-select.

**Start Date:** Filter data to only include records where Zendesk modified date is greater than 
or equal to the specified date. The date must be provided in the date format:

|              Format              |       Format Syntax       |          Example          |
| -------------------------------- | ------------------------- | ------------------------- |
| Date, time, and time zone offset | YYYY-MM-DDThh:mm:ss+hh:mm | 1999-01-01T23:01:01+01:00 |
|                                  | YYYY-MM-DDThh:mm:ss-hh:mm | 1999-01-01T23:01:01-08:00 |
|                                  | YYYY-MM-DDThh:mm:ssZ      | 1999-01-01T23:01:01Z      |

Start Date is required for batch objects like: Ticket Comments, Organizations, Ticket Metric Events, Tickets, Users.

**End Date:** Filter data to only include records where Zendesk modified date is less than 
the specified date. The date must be provided in the date format:

|              Format              |       Format Syntax       |          Example          |
| -------------------------------- | ------------------------- | ------------------------- |
| Date, time, and time zone offset | YYYY-MM-DDThh:mm:ss+hh:mm | 1999-01-01T23:01:01+01:00 |
|                                  | YYYY-MM-DDThh:mm:ss-hh:mm | 1999-01-01T23:01:01-08:00 |
|                                  | YYYY-MM-DDThh:mm:ssZ      | 1999-01-01T23:01:01Z      |

Specifying this along with `Start Date` allows reading data modified within a specific time window. 
If no value is provided, no upper bound is applied.

**Satisfaction Ratings Score:** Score filter for Satisfaction Ratings object.

### Advanced

**Max Retry Count:** Maximum number of retry attempts.

**Max Retry Wait:** Maximum time in seconds retries can take.

**Max Retry Jitter Wait:** Maximum time in milliseconds added to retries.

**Connect Timeout:** Maximum time in seconds connection initialization can take.

**Read Timeout:** Maximum time in seconds fetching data from the server can take.