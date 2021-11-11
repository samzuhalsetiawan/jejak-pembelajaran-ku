
let globalState = {
  counter: 0
}

const rootReducer = (state = globalState, action) => {
  switch (action.type) {
    case "TAMBAH_COUNTER":
      return {
        ...state,
        counter: state.counter + 1
      }
    case "KURANG_COUNTER":
      if (state.counter < 1) return state;
      return {
        ...state,
        counter: state.counter - 1
      }
    default:
      return state;
  }
}

export default rootReducer;