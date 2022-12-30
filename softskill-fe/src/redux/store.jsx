import { configureStore } from "@reduxjs/toolkit";
import rootReducer from "./reducers/root-reducer";

const store = configureStore({
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({
        serializableCheck: false,
        immutableCheck: false,
    }),
});

export default store;
