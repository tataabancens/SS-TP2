from turtle import st
import matplotlib.pyplot as plt
import math
import numpy as np
import sys

#if len(sys.argv) != 3:
#    print("Must specify the name of the file to parse and the type being parsed: Living or Radius")
#    exit(1)

file_name = sys.argv[1]
y_type = sys.argv[2]
f = open(file_name, "r")

# Parsing -> Dimension porcentaje regla valores

evolution = []
time = []
stats = {}

# Variables that must be changed depending on the regression line we want
show_regression = False
reg_time_limit = 50

for line in f:
    time = 0
    data = line.rstrip("\n").split(" ")
    percentage = data[0]
    rule = int(data[1])


    if rule not in stats:
        stats[rule] = {}
    if percentage not in stats[rule]:
        stats[rule][percentage] = {}
        stats[rule][percentage]['evolution'] = []
        stats[rule][percentage]['time'] = []

    while time < (len(data) - 3):
        stats[rule][percentage]['evolution'].append(float(data[time + 3]))
        stats[rule][percentage]['time'].append(float(time))
        time += 1

for rule in stats:

    plt.clf()

        # Set the x axis label
    plt.xlabel('Iterations')

        # Set the y axis label
    if y_type == 'Living':
        ylabel = 'Living cells'
    else:
        ylabel = 'Cells from center'
    plt.ylabel(ylabel)


    for percentage in stats[rule]:
        evolution = stats[rule][percentage]['evolution']
        time = stats[rule][percentage]['time']
            #create scatter plot
        label = 'N=' + str(int(int(percentage)/10))+'%'
        plt.plot(time, evolution, label=label)

        if show_regression:
                #m = slope, b=intercept
            m, b = np.polyfit(time, evolution, 1)

                # Creating the regression line
            regression = []
            for t in time:
                regression.append(m*t + b)

                # Plotting the regression line
                reg_label = 'Regression for rule ' + str(rule)
                plt.plot(time[:reg_time_limit], regression[:reg_time_limit], label=reg_label)

            #Labelling the lines
            
    plt.legend()
    plt.plot()
    plt.savefig('./images/' + y_type + ' rule '+str(rule)+ '.png')