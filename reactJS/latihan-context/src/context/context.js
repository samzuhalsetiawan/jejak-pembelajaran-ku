import React, {createContext, useState} from "react";

const RootContext = createContext();

export const GlobalProvider = (Children) => {
  return (
    function ParentComp() {
      const [count, changeCount] = useState(0);
      // Kita bisa membuat lebih dari 1 state dengan hooks
      const [count2, changeCount2] = useState({
        nama: "Sam",
        counter: 5
      });
      
      const dispatch = (action) => {
        switch (action.type) {
          case "TAMBAH":
            return changeCount(count + 1);
          case "KURANG":
            if (count <= 0) return;
            return changeCount(count - 1);
          case "TAMBAH2":
            changeCount2({
              ...count2,
              counter: count2.counter + 1
            });
            break;
          default:
            return;
        }
      }

      return (
        <RootContext.Provider value={{count, count2, dispatch}}>
          <Children />
        </RootContext.Provider>
      )
    }
  )
}

export const GlobalConsumer = (Children) => {
  return (
    function ParentComp() {
      return (
        <RootContext.Consumer>
          {
            value => {
              return (
                <Children {...value}/>
              )
            }
          }
        </RootContext.Consumer>
      )
    }
  )
}