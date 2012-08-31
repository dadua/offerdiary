fp = open('/home/ec2-user/orkut2fb-log')
lines = []
for line in fp:
    lines.append(line)

profileIds = []   
for line in lines:
    index=line.find('fbProfileId')
    profileIds.append(line[index:].split(',')[0].split('=')[1])

#for id in profileIds:
#    print id

print 'No of times export inited: ' + str(len(profileIds))

totalPhotosCounts = []
for line in lines:
    index=line.find('totalPhotoCount')
    totalPhotosCounts.append((line[index:].split(',')[0].split('=')[1])[:-1])

totalDownloads = 0
for p in totalPhotosCounts:
    totalDownloads += int(p)

print 'Total no. of photos downloaded: ' + str(totalDownloads)
