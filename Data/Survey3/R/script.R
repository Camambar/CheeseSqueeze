##########################################################
            # Data inlezen ##
##########################################################

setwd("/Users/maxdekoninck/Dropbox/Game/Data/Survey3/R")
library(ggplot2)
#In deze stap wordt de tabel ingeladen, tussen de waarden staat er niets, en er is een header die de kolom namen bevat.

winlose = read.csv("Analytics\ Alle\ gegevens\ voor\ mobiele\ app\ WIN_LOSE\ TO\ R\ 20141206-20141209.csv",sep=",", na.string="#", header = TRUE)
usersday = read.csv("Analytics\ Alle\ gegevens\ voor\ mobiele\ app\ USER_DAY\ 20141206-20141209.csv",sep=",", na.string="#", header = TRUE)
newvsret = read.csv("New_vs._Returning 20141119-20141125.csv",sep=",", na.string="#", header = TRUE)
location = read.csv("Location_20141119-20141125.csv",sep=",", na.string="#", header = TRUE)
loading =read.csv("Average_LOADINGTIME_20141119-20141125.csv",sep=",", na.string="#", header = TRUE)
completionLevel1 =read.csv("CompletionTime_LEVEL1_20141119-20141125.csv",sep=",", na.string="#", header = TRUE)
completionLevel2 =read.csv("CompletionTime_LEVEL2_20141119-20141125.csv",sep=",", na.string="#", header = TRUE)
firstlineLevel1 = read.csv("FirstLine_LEVEL1_20141119-20141125.csv",sep=",", na.string="#", header = TRUE)
avergcomptime = read.csv("Average_CompletionTime_ALL_20141119-20141125.csv",sep=",", na.string="#", header = TRUE)
firstlineLevel2 = read.csv("FirstLine_LEVEL2_20141119-20141125.csv",sep=",", na.string="#", header = TRUE)
question = read.csv("GameQuestionnaire (Responses).csv",sep=",", na.string="#", header = TRUE)
rating = read.csv("Analytics\ Alle\ gegevens\ voor\ mobiele\ app\ RATING\ 20141206-20141209.csv",sep=",", na.string="#", header = TRUE)


#Footnote param

scriptName <- "Analytics"
author <- "Cheese Squeeze"
dataperiod <- "2014/11/19-2014/11/25"

##########################################################
            # Footnote script ##
##########################################################

footnote <- paste(scriptName, dataperiod,
                  author, sep=" : ")

makeFootnote <- function(footnoteText=
                         format(Sys.time(), "%d %b %Y"),
                         size= .4, color= grey(.4))
{
   require(grid)
   pushViewport(viewport())
   grid.text(label= footnoteText ,
             x = unit(1,"npc") - unit(2, "mm"),
             y= unit(2, "mm"),
             just=c("right", "bottom"),
             gp=gpar(cex= size, col=color))
   popViewport()
}

makeFootnote(footnote)



##########################################################
            # Win lose voor de levels vegelijken ##
##########################################################


winloseLevels <- aggregate(cbind(Unique.Events.GAMEOVER,Unique.Events.WON) ~ Event.Action, data=winlose, FUN=sum)
winloseLevels $Event.Action = as.character(winloseLevels $Event.Action)
winloseLevels <- winloseLevels[ order(nchar(winloseLevels$Event.Action)), ]
ratio <- (winloseLevels$Unique.Events.WON / winloseLevels$Unique.Events.GAMEOVER)
winloseLevels  <- cbind(winloseLevels, ratio)
winloseLevels$ratio = as.numeric(winloseLevels $ratio)
winloseLevels$ratio = round(winloseLevels$ratio, digits = 2)
counts <- cbind(winloseLevels$Unique.Events.GAMEOVER, winloseLevels$Unique.Events.WON)
colnames(counts) <- c("LOSE", "WIN")
rownames(counts) <- winloseLevels$Event.Action

pdf("graph/winloseratio.pdf")
bplt1 <- barplot(t(counts), main="WIN/LOSE -Ratio/Level",beside=T,ylab="amount of unique games", col=c("indianred1","steelblue1"),legend = colnames(counts),cex.names=0.8, las=2, ylim=c(0,max(winloseLevels$Unique.Events.GAMEOVER,winloseLevels$Unique.Events.WON)+10))
ypos <- apply(counts, 1, max)
text(y= ypos+1, x= colMeans(bplt1), labels=winloseLevels$ratio)
makeFootnote(footnote)
dev.off()

##########################################################
            # Gebruikers / dag ##
##########################################################

usersday$Day.Index = as.character(usersday$Day.Index)
users <- cbind(usersday$Users)
rownames(users) <- usersday$Day.Index
colnames(users) <- c("USERS")

pdf("graph/usersday.pdf")
bplt2 <- barplot(t(users),main="USERS/DAY",ylab="amount of users",col=c("steelblue1"),cex.names=0.8, las=2, y
=c(0,max(usersday$Users)+5))
text(y= users +1, x= bplt2, labels=usersday$Users)
makeFootnote(footnote)
dev.off()

##########################################################
            # new vs return ##
##########################################################

slices <- cbind(newvsret$Users)
pct <- round(slices/sum(slices)*100)
lbls <- c(as.character(newvsret$User.Type))
lbls <- paste(lbls, pct)
lbls <- paste(lbls,"%",sep="")

pdf("graph/newvsreturn.pdf")
pie(slices,labels = lbls, col=c("springgreen2","steelblue1"),main="New And Returning Users")
makeFootnote(footnote)
dev.off()

newusers <- newvsret$Users[1]
returnusers <- newvsret$Users[2]
users <- c(newusers-returnusers,returnusers)

pdf("graph/newvsreturn.pdf")
percent_str <- paste(round(users / sum(users) * 100,1), "%", sep="")
values <- data.frame(users = users, Type = newvsret$User.Type, percent=percent_str )
pie <- ggplot(data =values, aes(x = "", y = users, fill = Type)) + geom_bar(width = 1,stat="identity") + geom_text(aes(y = users/2 + c(0, cumsum(users)[-length(users)]), label= percent), size=2+newvsret$Users/10)
pie <- pie + coord_polar(theta = "y")
pie <- pie + ggtitle("New And Returning Users")
pie <- pie + scale_fill_discrete(name="Type",
                         breaks=c("New Visitor","Returning Visitor"),
                         labels=c("Non-Returning","Returning"))
pie <- pie + labs(x="",y="")
print(pie)
makeFootnote(footnote)
dev.off()


##########################################################
            # location ##
##########################################################


pdf("graph/location.pdf")
percent_str <- paste(round(location$Users / sum(location$Users) * 100,1), "%", sep="")
values <- data.frame(users = location$Users, Country = location$Country.Territory, percent=percent_str )
pie <- ggplot(data =values, aes(x = "", y = users, fill = Country)) + geom_bar(width = 1,stat="identity") + geom_text(aes(y = users/2 + c(0, cumsum(users)[-length(users)]), label= percent), size=2+location$Users/10)
pie <- pie + coord_polar(theta = "y")
pie <- pie + ggtitle("Location")
pie <- pie + labs(x="",y="")

print(pie)
makeFootnote(footnote)
dev.off()

##########################################################
            # boxplot laoding time##
##########################################################
pdf("graph/loadingTime.pdf")
values <- data.frame(value = loading$Avg..Value)
box <- ggplot(values, aes(x="", y=value, fill="")) + geom_boxplot() + guides(fill=FALSE) + coord_flip()
box <- box + stat_summary(fun.y=mean, geom="point", shape=5, size=4)
box <- box + ggtitle("Loading Time")
box <- box + labs(x="",y="Time [sec]")
print(box)
makeFootnote(footnote)
dev.off()




##########################################################
            # boxplot completionLevel1##
##########################################################


pdf("graph/completionTimeLevel1.pdf")
values <- data.frame(value = completionLevel1$Event.Value.WON)
box <- ggplot(values, aes(x="", y=value, fill="")) + geom_boxplot() + guides(fill=FALSE) + coord_flip()
box <- box + stat_summary(fun.y=mean, geom="point", shape=5, size=4)
box <- box + ggtitle("Completion Time Level 1")
box <- box + labs(x="",y="Time [sec]")
print(box)
makeFootnote(footnote)
dev.off()


##########################################################
            # boxplot completionLevel2##
##########################################################


pdf("graph/completionTimeLevel2.pdf")
values <- data.frame(value = completionLevel2$Event.Value.WON)
box <- ggplot(values, aes(x="", y=value, fill="")) + geom_boxplot() + guides(fill=FALSE) + coord_flip()
box <- box + stat_summary(fun.y=mean, geom="point", shape=5, size=4)
box <- box + ggtitle("Completion Time Level 2")
box <- box + labs(x="",y="Time [sec]")
print(box)
makeFootnote(footnote)
dev.off()



##########################################################
            # boxplot completionLevel 1 2##
##########################################################


pdf("graph/completionTime.pdf")
counts1 <- cbind("LEVEL1",as.double(completionLevel1$Event.Value.WON))
counts2 <- cbind("LEVEL2",as.double(completionLevel2$Event.Value.WON))
counts <- rbind(counts1,counts2)
colnames(counts) <- c("LEVEL", "Time")
values <- data.frame(value = as.double(counts[,2]), Level = counts[,1])
box <- ggplot(values, aes(x= Level, y=value, fill=Level)) + geom_boxplot() + guides(fill=FALSE) + coord_flip()
box <- box + stat_summary(fun.y=mean, geom="point", shape=5, size=4)
box <- box + ggtitle("Completion Time")
box <- box + labs(x="",y="Time [sec]")
print(box)
makeFootnote(footnote)
dev.off()


##########################################################
            # boxplot firstline level 1##
##########################################################


pdf("graph/firstLineLevel1.pdf")
values <- data.frame(value = firstlineLevel1$Avg..Value)
box <- ggplot(values, aes(x="", y=value, fill="")) + geom_boxplot() + guides(fill=FALSE) + coord_flip()
box <- box + stat_summary(fun.y=mean, geom="point", shape=5, size=4)
box <- box + ggtitle("FirstLine Level 1")
box <- box + labs(x="",y="Time [sec]")
print(box)
makeFootnote(footnote)
dev.off()




##########################################################
            # boxplot firstline level 2##
##########################################################


pdf("graph/firstLineLevel2.pdf")
values <- data.frame(value = firstlineLevel2$Avg..Value.LEVEL2)
box <- ggplot(values, aes(x="", y=value, fill="")) + geom_boxplot() + guides(fill=FALSE) + coord_flip()
box <- box + stat_summary(fun.y=mean, geom="point", shape=5, size=4)
box <- box + ggtitle("FirstLine Level 2")
box <- box + labs(x="",y="Time [sec]")
print(box)
makeFootnote(footnote)
dev.off()

##########################################################
            # boxplot firstline level 1 en 2##
##########################################################
pdf("graph/firstLine.pdf")
counts1 <- cbind("LEVEL1",as.double(firstlineLevel1$Avg..Value))
counts2 <- cbind("LEVEL2",as.double(firstlineLevel2$Avg..Value.LEVEL2))
counts <- rbind(counts1,counts2)
colnames(counts) <- c("LEVEL", "Time")
values <- data.frame(value = as.double(counts[,2]), Level = counts[,1])
box <- ggplot(values, aes(x= Level, y=value, fill=Level)) + geom_boxplot() + guides(fill=FALSE) + coord_flip()
box <- box + ggtitle("FirstLine")
box <- box + labs(x="",y="Time [sec]")
print(box)
makeFootnote(footnote)
dev.off()


##########################################################
            # boxplot averg comp time all##
##########################################################

pdf("graph/compTime.pdf")
avergcomptime <- avergcomptime[ order(avergcomptime$Event.Action), ]
avergcomptime$Event.Action = as.character(avergcomptime$Event.Action)
avergcomptime <- avergcomptime[ order(nchar(avergcomptime$Event.Action)), ]
values <- data.frame(value = avergcomptime$Avg..Value.WON, type = factor(avergcomptime$Event.Action, levels=unique(avergcomptime$Event.Action)))
box <- ggplot(values, aes(x=type, y=value, fill=type)) + geom_boxplot() + guides(fill=FALSE) + coord_flip()
box <- box + ggtitle("Completion Time")
box <- box + labs(x="",y="Time [sec]")
print(box)
makeFootnote(footnote)
dev.off()

##########################################################
            # boxplot questionaire##
##########################################################

pdf("graph/gender.pdf")

perc <- round(nrow(subset(question, Gender=="Male")) / length(question$Gender) * 100,1)
percent_str <- c(paste(perc, "%", sep=""),paste(100-perc, "%", sep=""))

values <- data.frame(Users = c("Male","Female"), amount = c(nrow(subset(question, Gender=="Male")),length(question$Gender)-nrow(subset(question, Gender=="Male"))) ,percent=percent_str )
pie <- ggplot(data =values, aes(x = "", y = amount, fill = Users)) + geom_bar(width = 1,stat="identity") + geom_text(aes(y = amount/2 + c(0, cumsum(amount)[-length(amount)]), label= percent), size=8)
pie <- pie + coord_polar(theta = "y")
pie <- pie + ggtitle("User Gender")
pie <- pie + scale_fill_discrete(name="Users")
pie <- pie + labs(x="",y="")

print(pie)
makeFootnote(footnote)
dev.off()

##########################################################
            # clean questionair##
##########################################################


question$Clarity <- lapply(strsplit(as.character(question$Clarity),"[.]"),"[", 1)
question$Flow <- lapply(strsplit(as.character(question$Flow),"[.]"),"[", 1)
question$Balance <- lapply(strsplit(as.character(question$Balance),"[.]"),"[", 1)
question$Length <- lapply(strsplit(as.character(question$Length),"[.]"),"[", 1)
question$Integration <- lapply(strsplit(as.character(question$Integration),"[.]"),"[", 1)
question$Fun <- lapply(strsplit(as.character(question$Fun),"[.]"),"[", 1)


data1 <- cbind("Clarity",as.double(question$Clarity))
data2 <- cbind("Flow", as.double(question$Flow))
data3 <- cbind("Balance", as.double(question$Balance))
data4 <- cbind("Length", as.double(question$Length))
data5 <- cbind("Integration", as.double(question$Integration))
data6 <- cbind("Fun", as.double(question$Fun))
data <- rbind(data1,data2,data3,data4,data5,data6)
colnames(data) = c("Type","value")

values <- data.frame(value = as.double(data[,2]),type = data[,1])
pdf("graph/questionair.pdf")
box <- ggplot(values, aes(x=type, y=value, fill=type)) + geom_boxplot() + guides(fill=FALSE) + coord_flip()
box <- box + ggtitle("Questionair")
box <- box + labs(x="",y="")
box <- box + coord_cartesian(ylim = c(1, 7)) 
print(box)
makeFootnote(footnote)
dev.off()

##########################################################
            # clean Rating ##
##########################################################

pdf("graph/rating.pdf")
rating <- rating[ order(rating$Event.Action), ]
rating$Event.Action = as.character(rating$Event.Action)
rating <- rating[ order(nchar(rating$Event.Action)), ]
values <- data.frame(value = rating $Avg..Value, type = factor(rating $Event.Action, levels=unique(rating$Event.Action)))
box <- ggplot(values, aes(x=type, y=value, fill=type)) + geom_boxplot() + guides(fill=FALSE) + coord_flip()
box <- box + ggtitle("Completion Time")
box <- box + labs(x="",y="Rating ")
print(box)
makeFootnote(footnote)
dev.off()

