update CARDS set PAYMENT_CHANNEL = 'UNKNOWN' where PAYMENT_CHANNEL is null;
update OFFERS set SOURCE_TYPE = 'UNKOWN' where SOURCE_TYPE is null;
update OFFERS set APPROVED = 0 where APPROVED is null;