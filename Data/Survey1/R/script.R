##########################################################
            # Data inlezen ##
##########################################################

setwd("/Users/maxdekoninck/Dropbox/CSGame3/Data/Survey1/R")

#In deze stap wordt de tabel ingeladen, tussen de waarden staat er niets, en er is een header die de kolom namen bevat.

data = read.csv("enquettedata.csv",sep=",", na.string="#", header = TRUE)


pdf("graphs/boxplots.pdf")
par(mfrow=c(1,6))
boxplot(data$Clarity,ylim=c(1,7),main="Clarity")
boxplot(data$Flow,ylim=c(1,7),main="Flow")
boxplot(data$Balance,ylim=c(1,7),main="Balance")
boxplot(data$Length,ylim=c(1,7),main="Length")
boxplot(data$Integration,ylim=c(1,7),main="Integration")
boxplot(data$Fun,ylim=c(1,7),main="Fun")
dev.off()

pdf("graphs/gamplayGraphs.pdf")
par(mfrow=c(1,2))
boxplot(data$Level1CompleteTime)
boxplot(data$Level2CompleteTime)
dev.off()

pdf("graphs/failrate.pdf")
par(mfrow=c(1,1))
hist(data$Failrate)
dev.off()

#pdf("graphs/LevelComplete.pdf")
#boxplot(data$Level1CompleteTime)
#dev.off()