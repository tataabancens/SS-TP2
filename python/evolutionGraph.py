import matplotlib.pyplot as plt
import numpy as np
import sys
import re

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


    if percentage not in stats:
        stats[percentage] = {}
    if rule not in stats[percentage]:
        stats[percentage][rule] = {}
        stats[percentage][rule]['evolution'] = []
        stats[percentage][rule]['time'] = []

        while time < (len(data) - 3):
            stats[percentage][rule]['evolution'].append(float(data[time + 3]))
            stats[percentage][rule]['time'].append(float(time))
            time += 1

for percentage in stats:
        plt.clf()

        # Set the x axis label
        plt.xlabel('Time (iterations)')

        # Set the y axis label
        if y_type == 'Living':
            ylabel = 'Percentage of Living Cells (%)'
        else:
            ylabel = 'Maximum displacement from center'
        plt.ylabel(ylabel)

        # Set a title of the current graph.
        plt.title( ylabel + ' vs. Time')

        for rule in stats[percentage]:
            evolution = stats[percentage][rule]['evolution']
            time = stats[percentage][rule]['time']

            #create scatter plot
            label = 'Rule ' + str(rule)
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

        plt.savefig('../images/' + y_type + str(int(float(percentage))) + '.png')