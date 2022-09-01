import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

rule = "2"

df = pd.read_json('results\\rule' + rule + '\\finalAliveCells.json')

x = df['N']
y = df['aliveCellsProm']
yerror = df['error']

plt.errorbar(x,y,yerr=yerror, fmt='o')
plt.xlabel("N (%)")
plt.ylabel("Celdas vivas finales")
plt.show()

print(df)

