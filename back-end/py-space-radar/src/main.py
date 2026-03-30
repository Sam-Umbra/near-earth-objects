from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import joblib
import pandas as pd
from src.config import MODELS_DIR

app = FastAPI(
    title="Space radar ML API",
    description="Real-Time asteroids classification API",
    version="1.0.0",
)

try:
    model = joblib.load(MODELS_DIR / "radar_model.joblib")
    scaler = joblib.load(MODELS_DIR / "scaler.joblib")
    print("Model and scaler loaded with success")
except Exception as e:
    print(f"Error upon loading models. Run train.py first. Details: {e}")


class AsteroidData(BaseModel):
    est_diameter_min: float
    est_diameter_max: float
    relative_velocity: float
    miss_distance: float
    absolute_magnitude: float


@app.post("/predict")
async def predict_hazard(data: AsteroidData):
    try:
        input_dict = {
            "est_diameter_min": data.est_diameter_min,
            "relative_velocity": data.relative_velocity,
            "miss_distance": data.miss_distance,
            "absolute_magnitude": data.absolute_magnitude,
            "est_diameter_max": data.est_diameter_max,
            "est_diameter_avg": (data.est_diameter_min + data.est_diameter_max) / 2,
        }

        X_input = pd.DataFrame([input_dict])

        X_scaled = scaler.transform(X_input)

        prediction = model.predict(X_scaled)[0]
        probability = model.predict_proba(X_scaled)[0][1]

        return {
            "is_hazardous": bool(prediction),
            "confidence_score": round(float(probability), 4),
            "status": "success",
        }
    except Exception as e:
        print(f"Processing Error: {e}")
        raise HTTPException(
            status_code=500, detail=f"Internal processing error: {str(e)}"
        )


@app.get("/health")
async def health_check():
    return {"status": "online", "model_loaded": model is not None}
