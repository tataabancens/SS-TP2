import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

rule = "2"

df = pd.read_json('results\\rule' + rule + '\\finalDistances.json')

x = df['N']
y = df['maxDistanceProm']
yerror = df['error']

plt.errorbar(x,y,yerr=yerror, fmt='o')
plt.xlabel("N (%)")
plt.ylabel("Distancia final (celdas)")
plt.show()

print(df)

