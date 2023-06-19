{{ define "k8s.currentDate" }}
currentDate: {{ now | htmlDate | quote }}
version: {{ .Chart.Version | quote }}
{{- end }}
