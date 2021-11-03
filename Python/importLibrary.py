import math
import numpy as np
import pandas as pd
import seaborn as sns

# using math
print(math.factorial(5))

# using numpy
arrayKu = np.array([
    [1, 2, 3],
    ["Sam", "Zuhal", "Setiawan"]
])
print(arrayKu)
arrayKu2 = np.arange(15).reshape(3, 5)
print(arrayKu2)

# using pandas
sample = pd.Series([1, 3, 5, np.nan, 6, 8])
print(sample)
tanggal = pd.date_range("20210810", periods=6)
print(tanggal)

# using seaborn
df = sns.load_dataset("penguins")
sns.pairplot(df, hue="species")
