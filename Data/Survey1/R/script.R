##########################################################
            # Data inlezen ##
##########################################################

setwd("/Users/maxdekoninck/Dropbox/CSGame3/Data/Survey1/R")

#In deze stap wordt de tabel ingeladen, tussen de waarden staat er niets, en er is een header die de kolom namen bevat.

data = read.csv("enquettedata.csv",sep=",", na.string="#", header = TRUE)

boxplot(data$Age)