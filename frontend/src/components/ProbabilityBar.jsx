function ProbabilityBar({label, probability}) {
    return (
        <div className = "probability-row">
            <span className = "probability-label">
                {label}
            </span>
            <div className = "probability-bar-background">
                <div
                    className = "probability-bar-fill"
                    style = {{ width: `${probability * 100}%`}}
                />
            </div>
            <span className = "probability-percent">
                {(probability * 100).toFixed(0)}%
            </span>
        </div>
    );
}

export default ProbabilityBar;